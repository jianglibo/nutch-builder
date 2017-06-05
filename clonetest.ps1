function Get-OriginPackageBase {
    $AllDirs = Join-Path $PSScriptRoot -ChildPath "src/main/java" | Get-ChildItem -Recurse -Directory
    $maxCount = 0
    $originBase = ""
    foreach ($OneDir in $AllDirs) {
        $c = (Get-ChildItem $OneDir.FullName -Directory | Measure-Object).Count 
        if ($c -gt $maxCount) {
            $maxCount = $c
            $originBase = $OneDir
        }
    }
    if ($originBase.FullName -match "^(.*)\\src\\main\\java\\(.*)$") {
        ($Matches[2] -split "\\") -join "."
    }
}

$originPackageBase = Get-OriginPackageBase

Join-Path $PSScriptRoot -ChildPath ("scr\main\java\" + ($originPackageBase -replace "\.", "\"))