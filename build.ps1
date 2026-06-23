$ErrorActionPreference = "Stop"

$sourceRoot = Join-Path $PSScriptRoot "src\main\java"
$outputRoot = Join-Path $PSScriptRoot "out"

if (Test-Path $outputRoot) {
    Remove-Item -Recurse -Force $outputRoot
}

New-Item -ItemType Directory -Force -Path $outputRoot | Out-Null

$sources = Get-ChildItem -Path $sourceRoot -Recurse -Filter "*.java" |
    ForEach-Object { $_.FullName }

if (-not $sources) {
    throw "No Java source files found under $sourceRoot"
}

javac -encoding UTF-8 -cp $sourceRoot -d $outputRoot $sources
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Host "Build succeeded. Run with: java -cp out ais.app.AISMain2"
