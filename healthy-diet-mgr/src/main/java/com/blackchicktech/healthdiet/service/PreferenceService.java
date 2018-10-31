package com.blackchicktech.healthdiet.service;

import com.blackchicktech.healthdiet.domain.PreferenceResponse;
import com.blackchicktech.healthdiet.entity.Preference;
import com.blackchicktech.healthdiet.repository.PreferenceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferenceService {
	@Autowired
	private PreferenceDaoImpl preferenceDao;

	public PreferenceResponse save(Preference preference) {
		preferenceDao.savePreference(preference);
		PreferenceResponse response = new PreferenceResponse();
		response.setMessage("更新成功！");
		return response;
	}

	/**
	 *
	 * @param userId 用户唯一id
	 * @param itemId 菜品id
	 * @param type recipe
	 * @return
	 */
	public PreferenceResponse listPreference(String userId, String itemId, String type) {
		//用户对该菜品的喜好程度
		Preference preference = preferenceDao.getPreference(userId, itemId, type);
		PreferenceResponse preferenceResponse = new PreferenceResponse();
		Optional.ofNullable(preference).ifPresent(item -> preferenceResponse.setPreference(preference.getFrequency()));
		return preferenceResponse;
	}
}
