pm2 delete users-service 2>$null

Get-Content .env | ForEach-Object {
    if ($_ -notmatch '^\s*#' -and $_ -match '=') {
        $key, $value = $_ -split '=', 2
        [System.Environment]::SetEnvironmentVariable($key.Trim(), $value.Trim(), "Process")
    }
}

pm2 start .\presentations\build\libs\presentations-0.0.1-SNAPSHOT.jar --interpreter java --interpreter-args "-jar" --name users-service
