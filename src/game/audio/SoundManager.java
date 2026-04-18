package game.audio;

import javax.sound.midi.*;
import java.util.concurrent.*;

public class SoundManager {
    private static Synthesizer synth;
    private static MidiChannel[] channels;
    private static Sequencer sequencer;
    private static boolean initialized = false;
    private static ExecutorService pool;
    private static Process bgmProcess;
    
    // Channels
    private static final int CH_PERCUSSION = 9; // MIDI channel 10 is percussion
    private static final int CH_MELODY = 0;
    private static final int CH_FX = 1;
    
    public static void init() {
        if (initialized) return;
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            
            pool = Executors.newCachedThreadPool();
            
            // Sequencer for background music
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            
            initialized = true;
        } catch (Exception e) {
            System.err.println("Failed to initialize MIDI: " + e.getMessage());
        }
    }
    
    public static void playClick() {
        if (!initialized) return;
        pool.execute(() -> {
            channels[CH_PERCUSSION].noteOn(76, 127); // Woodblock
            try { Thread.sleep(50); } catch (Exception e) {}
            channels[CH_PERCUSSION].noteOff(76);
        });
    }

    public static void playFight() {
        if (!initialized) return;
        pool.execute(() -> {
            channels[CH_PERCUSSION].noteOn(43, 127); // High Floor Tom
            try { Thread.sleep(70); } catch (Exception e) {}
            channels[CH_PERCUSSION].noteOn(41, 127); // Low Floor Tom
            try { Thread.sleep(100); } catch (Exception e) {}
            channels[CH_PERCUSSION].noteOff(43);
            channels[CH_PERCUSSION].noteOff(41);
        });
    }

    public static void playDamage() {
        if (!initialized) return;
        pool.execute(() -> {
            channels[CH_PERCUSSION].noteOn(49, 127); // Crash Cymbal
            try { Thread.sleep(300); } catch (Exception e) {}
            channels[CH_PERCUSSION].noteOff(49);
        });
    }

    public static void playHeal() {
        if (!initialized) return;
        pool.execute(() -> {
            channels[CH_MELODY].programChange(11); // Vibraphone
            int[] notes = {60, 64, 67, 72};
            for (int note : notes) {
                channels[CH_MELODY].noteOn(note, 90);
                try { Thread.sleep(80); } catch (Exception e) {}
            }
            try { Thread.sleep(300); } catch (Exception e) {}
            for (int note : notes) {
                channels[CH_MELODY].noteOff(note);
            }
        });
    }

    public static void playPuzzle() {
        if (!initialized) return;
        pool.execute(() -> {
            channels[CH_FX].programChange(98); // Crystal
            channels[CH_FX].noteOn(72, 100);
            try { Thread.sleep(150); } catch (Exception e) {}
            channels[CH_FX].noteOn(79, 100);
            try { Thread.sleep(150); } catch (Exception e) {}
            channels[CH_FX].noteOn(84, 110);
            try { Thread.sleep(600); } catch (Exception e) {}
            channels[CH_FX].noteOff(72);
            channels[CH_FX].noteOff(79);
            channels[CH_FX].noteOff(84);
        });
    }

    public static void playWin() {
        if (!initialized) return;
        stopTheme();
        pool.execute(() -> {
            channels[CH_MELODY].programChange(56); // Trumpet
            int[] notes = {60, 60, 60, 64, 67};
            int[] times = {150, 150, 150, 400, 800};
            for (int i=0; i<notes.length; i++) {
                channels[CH_MELODY].noteOn(notes[i], 127);
                try { Thread.sleep(times[i]); } catch (Exception e) {}
                channels[CH_MELODY].noteOff(notes[i]);
                try { Thread.sleep(50); } catch (Exception e) {}
            }
        });
    }

    public static void playGameOver() {
        if (!initialized) return;
        stopTheme();
        pool.execute(() -> {
            channels[CH_MELODY].programChange(19); // Church Organ
            int[] notes = {48, 47, 46, 45, 42};
            for (int note : notes) {
                channels[CH_MELODY].noteOn(note, 100);
                try { Thread.sleep(400); } catch (Exception e) {}
                channels[CH_MELODY].noteOff(note);
            }
        });
    }

    public static void playTheme() {
        if (!initialized) return;
        stopTheme();
        try {
            String mp3Path = "c:\\Users\\Nirmal\\OneDrive\\Desktop\\Downloads\\starostin-comedy-cartoon-funny-background-music-492540.mp3";
            String[] cmd = {"powershell", "-ExecutionPolicy", "Bypass", "-WindowStyle", "Hidden", "-File", "play_bgm.ps1", "-path", "'" + mp3Path + "'"};
            bgmProcess = Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopTheme() {
        if (bgmProcess != null) {
            bgmProcess.destroy();
            bgmProcess = null;
        }
        if (sequencer != null && sequencer.isRunning()) {
            sequencer.stop();
        }
    }
}
