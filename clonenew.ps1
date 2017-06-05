<#
    copy all first, then delete unwanted, modify need modifing.
#>
Param(
    [parameter(Mandatory=$true)]$DstFolder,
    [parameter(Mandatory=$true)]$RootPackage,
    [string]$InitVersion="0.1.0"
)

$ignores = ".gradle", ".settings","bin",".vscode",".git"

$templateProject = $PSScriptRoot

if (($RootPackage -split "\.").Count -lt 3) {
    "At least 3 segments in package." | Write-Error
}

if (-not (Split-Path $DstFolder -IsAbsolute)) {
    $DstFolder = $templateProject | Split-Path -Parent | Join-Path -ChildPath $DstFolder
}

if (Test-Path $DstFolder) {
    "$DstFolder already exists. stop clone!" | Write-Host -ForegroundColor Red
    # return
} else {
    New-Item -Path $DstFolder -ItemType Directory
}

# copy all
Copy-Item -Path "${templateProject}/**" -Destination $DstFolder -Recurse

# remove unnecessary items.
$ignores | ForEach-Object {Join-Path $DstFolder -ChildPath $_} |Where-Object {Test-Path $_} | Remove-Item -Recurse

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

function move-javafiles {
    Param([parameter(ValueFromPipeline=$true)]$srcFolders)
    $originPath = Join-Path $DstFolder -ChildPath ($srcFolders + ($originPackageBase -replace "\.", "\"))
    $newPath = Join-Path $DstFolder -ChildPath ($srcFolders + ($RootPackage -replace "\.", "\"))
    $newPathp = Split-Path $newPath -Parent
    if (-not (Test-Path $newPathp -PathType Container)) {
        New-Item $newPathp -ItemType Directory
    }
    Move-Item $originPath -Destination $newPath
}

move-javafiles "src\main\java\"
move-javafiles "src\test\java\"

# change all java files.

Get-ChildItem -Path $DstFolder -Recurse -File `
| Where-Object Name -Match "\.java$" `
| ForEach-Object {$_.FullName} `
| ForEach-Object {
    $currentFile = $_
    $originLines = Get-Content -Path $currentFile -Encoding UTF8
    $newLines = @()
    foreach ($line in $originLines) {
        $newLines += $line -replace $originPackageBase, ${RootPackage}
    }
    $newLines | Out-File $currentFile -Encoding utf8
}
    
