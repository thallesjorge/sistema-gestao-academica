# PowerShell build and run script for Academic Management System
$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $scriptDir

Write-Host "==> Compiling Academic Management System..." -ForegroundColor Cyan
if (!(Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

$javaFiles = Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d bin -sourcepath src $javaFiles

if ($LASTEXITCODE -eq 0) {
    Write-Host "==> Compilation successful!" -ForegroundColor Green
    Write-Host "==> Running application.Main..." -ForegroundColor Cyan
    java "-Dfile.encoding=UTF-8" -cp bin application.Main
} else {
    Write-Host "==> Compilation failed with exit code $LASTEXITCODE" -ForegroundColor Red
}
