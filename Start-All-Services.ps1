$subfolders = @(
  "eureka-server",
  "config-server",
  "auth-service",
  "employee-service",
  "specialty-service",
  "doctor-service",
  "patient-service",
  "office-service",
  "schedule-service",
  "appointment-service",
  "medical-history-service",
  "receipt-service",
  "medical-attention-service",
  # ----> Añadir aquí todos los microservicios
  "gateway-service"
)

$paths = $subfolders | ForEach-Object { Join-Path -Path $PSScriptRoot -ChildPath $_ }

$tabs = @()

1..$paths.Count | ForEach-Object {
  $i = $_
  $totalDelay = ($i - 1) * 7 # [7seg de delay entre ejecucion]
  $path = $paths[$i - 1]
  $folderName = $subfolders[$i - 1]

  $scriptContent = if ($totalDelay -eq 0) {
    @"
Set-Location -Path '$path'
mvn spring-boot:run
Start-Sleep -Seconds 15
"@
  }
  else {
    @"
Start-Sleep -Seconds $totalDelay
Set-Location -Path '$path'
mvn spring-boot:run
Start-Sleep -Seconds 15
"@
  }

  $tabs += if ($i -eq 1) {
    "--title `"$folderName`" pwsh -NoExit -Command `"$scriptContent`""
  }
  else {
    "new-tab --title `"$folderName`" pwsh -NoExit -Command `"$scriptContent`""
  }
}

Start-Process wt -ArgumentList ($tabs -join " ; ")