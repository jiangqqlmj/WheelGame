package com.szw.autoscrollview.anim;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * ����Ч
 * @author gary
 *
 */
public class GameSoundPool {
	private Context mContext;

	public GameSoundPool(Context context) {
		mContext = context;
		initSounds();
	}

	// ��Ч������
	int streamVolume;

	// ����SoundPool����
	private SoundPool soundPool;

	// ����HASH��
	private HashMap<Integer, Integer> soundPoolMap;

	/***************************************************************
	 * Function:initSounds(); Parameters:null Returns:None. Description:��ʼ������ϵͳ
	 * Notes:none.
	 ***************************************************************/
	public void initSounds() {
		// ��ʼ��soundPool����,��һ�������������ж��ٸ�������ͬʱ����,��2����������������,������������������Ʒ��
		soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);

		// ��ʼ��HASH��
		soundPoolMap = new HashMap<Integer, Integer>();

		// ��������豸���豸����
		AudioManager mgr = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/***************************************************************
	 * Function:loadSfx(); Parameters:null Returns:None. Description:������Ч��Դ
	 * Notes:none.
	 ***************************************************************/
	public void loadSfx(int raw, int ID) {
		// ����Դ�е���Ч���ص�ָ����ID(���ŵ�ʱ��Ͷ�Ӧ�����ID���ž�����)
		soundPoolMap.put(ID, soundPool.load(mContext, raw, ID));
	}

	/***************************************************************
	 * Function:play(); Parameters:sound:Ҫ���ŵ���Ч��ID,loop:ѭ������ Returns:None.
	 * Description:�������� Notes:none.
	 ***************************************************************/
	public void play(int sound, int uLoop) {
		soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1,
				uLoop, 1f);
		
	}
}
