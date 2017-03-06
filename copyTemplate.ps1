Param(
    [parameter(Mandatory=$true)]$TplFolder,
    $Tplname="apache-nutch-2.3.1"
)

$Dst = $PSCommandPath | Split-Path -Parent | Join-Path -ChildPath "templateRoot" | Join-Path -ChildPath $Tplname

if (Test-Path $Dst) {
    Remove-Item -Path $Dst -Recurse -Force
}

$expel = "*.jar","*.job","*.class",".settings","build","runtime","target",".classpath",".git",".git*",".project"

New-Item -Path $Dst -Type Directory
Copy-Item -Path "$TplFolder/*" -Destination $Dst -Exclude $expel -Recurse


