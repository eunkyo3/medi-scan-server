# UTF-8 console for Korean log output
$OutputEncoding = [Console]::OutputEncoding = [Text.Encoding]::UTF8
chcp 65001 | Out-Null
.\gradlew.bat bootRun
