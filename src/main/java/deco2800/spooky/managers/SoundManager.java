package deco2800.spooky.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;

/**
 * manage the game sound
 */
public class SoundManager implements AbstractManager {

    private ArrayList<Long>soundIdList = new ArrayList<>();

    private ArrayList<Sound>soundList = new ArrayList<>();

    private ArrayList<Sound>backgroundList = new ArrayList<>();

    private boolean soundPlay = false;

    private float volume = 1;

    private int index = 0;

    private static final String PATH = "resources/sounds/";
	
    /**
     * play a sound
     * @param soundName the name of the sound
     */
    public void playSound(String soundName, float volume) {
        long a;
        soundPlay = true;
        if (Gdx.audio != null) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(PATH + soundName));
            a = sound.play(volume);
            soundList.add(sound);
            soundIdList.add(a);
        }

    }

    /**
     * play a sound infinitely
     * @param soundName the sound to be played
     */
    public void playSoundLoop(String soundName) {
        long a;
        soundPlay = true;
        if (Gdx.audio != null) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(PATH + soundName));
            a = sound.loop(volume);
            soundList.add(sound);
            soundIdList.add(a);
        }
    }

    /**
     * mute all sound
     */
    public void muteSound() {
        if (soundPlay) {
            for (Sound sound : soundList) {
                for (Long id : soundIdList) {
                    sound.setVolume(id, 0);
                }
            }
            soundPlay = false;
        } else {
            for (Sound sound : soundList) {
                for (Long id : soundIdList) {
                    sound.setVolume(id, 1);
                }
            }
            soundPlay = true;
        }
    }

    /**
     * mute specific sound
     */
    public void muteSoundName(String soundName) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(PATH + soundName));
        sound.stop();
        soundPlay = false;
    }

    /**
     * mute background sound
     */
    public void muteBackgroundsound() {
        for (Sound background : backgroundList) {
            background.setVolume(index, 0);
        }
    }

    /**
     * make sound volume smaller
     */
    public void smallerSound() {
        if (volume == 0){
            return;
        }
        for (Sound sound : soundList) {
            for (Long id : soundIdList) {
                volume -= 0.1f;
                sound.setVolume(id, volume);
            }
        }

        for (Sound backgroundSound : backgroundList) {
            for (Long id : soundIdList) {
                volume -= 0.1f;
                backgroundSound.setVolume(id, volume);
            }
        }
    }

    /**
     * make sound volume bigger
     */
    public void louderSound() {
        if (volume == 1){
            return;
        }
        for (Sound sound : soundList) {
            for (Long id : soundIdList) {
                volume += 0.1f;
                sound.setVolume(id, volume);
            }
        }

        for (Sound backgroundSound: backgroundList) {
            for (Long id : soundIdList) {
                volume += 0.1f;
                backgroundSound.setVolume(id, volume);
            }
        }
    }

    /**
     * add background sound
     * @param soundName sound to be played
     */
    private void addSoundBackground(String soundName) {
        if (Gdx.audio != null) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(PATH + soundName));
            backgroundList.add(sound);
        }
    }

    /**
     * background sound list
     */
    public void backgroundList() {

    }

    /**
     * play background sound
     */
    public void playBackground() {
        long a;
        Sound background = backgroundList.get(index);

        a = background.loop();
        soundList.add(background);
        soundIdList.add(a);
    }

    /**
     * change background sound
     */
    public void changeBackground() {
        long a;
        Sound background = backgroundList.get(index);
        background.stop();

        index++;
        try {
            Sound newBackground = backgroundList.get(index);
            a = newBackground.loop();
            soundList.add(newBackground);
            soundIdList.add(a);
        } catch (IndexOutOfBoundsException e) {
            index = 0;
            Sound newBackground = backgroundList.get(index);
            a = newBackground.loop();
            soundList.add(newBackground);
            soundIdList.add(a);
        }
    }

    /**
     * change background sound
     */
    public void changeBackgroundBackward() {
        long a;
        Sound background = backgroundList.get(index);
        background.stop();

        index--;
        try {
            Sound newBackground = backgroundList.get(index);
            a = newBackground.loop();
            soundList.add(newBackground);
            soundIdList.add(a);
        } catch (IndexOutOfBoundsException e) {
            index = 0;
            Sound newBackground = backgroundList.get(index);
            a = newBackground.loop();
            soundList.add(newBackground);
            soundIdList.add(a);
        }
    }


    /**
     * get if sound is playing or not
     * @return sound is being played or not
     */
    public boolean getSoundPlay() {
        return soundPlay;
    }

}
