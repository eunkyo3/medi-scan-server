# UTF-8 console for Korean log output
$OutputEncoding = [Console]::OutputEncoding = [Text.Encoding]::UTF8
chcp 65001 | Out-Null
# Read the .env file, set environment variables, and then run
Get-Content .env | ForEach-Object {
    if ($_ -match '^\s*([^#][^=]+)=(.*)$') {
        [Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim(), 'Process')
    }
}
.\gradlew.bat bootRun