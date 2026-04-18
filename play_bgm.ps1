param([string]$path)

Add-Type -AssemblyName presentationCore
$mediaPlayer = New-Object system.windows.media.mediaplayer

$mediaPlayer.Open($path)

Register-ObjectEvent -InputObject $mediaPlayer -EventName MediaEnded -Action {
    $EventArgs.Source.Position = [TimeSpan]::Zero
    $EventArgs.Source.Play()
} | Out-Null

$mediaPlayer.Play()

# Keep script running to allow background event loop, wait infinitely
while($true) { Start-Sleep -Milliseconds 500 }
