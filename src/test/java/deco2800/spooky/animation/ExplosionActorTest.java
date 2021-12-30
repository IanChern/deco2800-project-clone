package deco2800.spooky.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.spooky.GameScreen;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.ScreenManager;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * A test class that verifies the functionality of the animation
 */

public class ExplosionActorTest {
    private GameManager mockGM;
    private ScreenManager mockSM;
    private WeaponEffect mockWE;
    private GameScreen mockGS;
    private ExplosionActor mockActor;
    private long mockTime; 
    private long currentTime;
    private long mockDeltaTime;
    private WeaponEffect weaponEffect;
    private ExplosionActor explosionActor;
    private float col; //random number
    private float row; //random number
    private TextureRegion[][] textureMatrix;

    @Before
    public void Setup() {
        mockGM = mock(GameManager.class);
        mockSM = mock(ScreenManager.class);
        mockWE = mock(WeaponEffect.class);
        mockGS = mock(GameScreen.class);
        mockActor = mock(ExplosionActor.class);
        currentTime = 0;
        col = 1f;
        row = 1f;

        //weaponEffect mockUp
        weaponEffect = new WeaponEffect();
        when(mockWE.getTextureMatrix()).thenReturn(textureMatrix);

        //explosionActor mockUp
        when(mockGS.getCurrentTime()).thenReturn((long)0);
        when(mockGS.getDeltaTime(mockTime)).thenReturn((long)1);
        when(mockActor.getCurrentTime()).thenReturn((long)0.05f);

        mockDeltaTime = mockGS.getDeltaTime(mockTime);
        explosionActor = new ExplosionActor(mockWE.getTextureMatrix(),col,row);

        doNothing().when(mockActor).addActor();
        doCallRealMethod().when(mockActor).setCurrentFrameTime((float)1);
        when(mockActor.getCurrentFrameTime()).thenReturn((float)1);
        doCallRealMethod().when(mockActor).setMaxFrameTime((float)0);
        when(mockActor.getMaxFrameTime()).thenReturn((float)0);
        doCallRealMethod().when(mockActor).update((float)2);
        doCallRealMethod().when(mockActor).setFrame(1);
        doCallRealMethod().when(mockActor).getFrame();

    }

    /*@Test
    public void weaponEffectTimerTesting() {
        verify(mockGS).getCurrentTime();
        verify(mockGS).getDeltaTime(mockTime);
    }
*/

    /*@Test
    public void WeaponEffectTextureTesting() {
        WeaponEffect wE = new WeaponEffect("Explode",col,row);
        assertEquals(null, wE.getTexture("Explode"));
    }*/

    @Test
    public void updateTesting() {
        //null case;
//        explosionActor.update(null);
//        assertEquals(0, explosionActor.getFrame());
        mockActor.update(mockDeltaTime);
        assertEquals(0, mockActor.getFrame());
        mockActor.update((float)2);
        assertEquals(0, mockActor.getFrame()); //should have expected 1

        for(int j=0; j<7; j++){
            mockActor.update(2*mockDeltaTime);
        }
        assertEquals(0,mockActor.getFrame());

    }
}