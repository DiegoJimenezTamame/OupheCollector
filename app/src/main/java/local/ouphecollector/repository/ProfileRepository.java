package local.ouphecollector.repository;

import android.app.Application;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.ProfileDao;
import local.ouphecollector.models.Profile;

import java.util.List;

public class ProfileRepository {
    private ProfileDao profileDao;

    public ProfileRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        profileDao = db.profileDao();
    }

    public List<Profile> getAllProfiles() {
        return profileDao.getAllProfiles();
    }

    public Profile getProfileById(int profileId) {
        return profileDao.getProfileById(profileId);
    }

    public void insertProfile(Profile profile) {
        profileDao.insert(profile);
    }

    public void updateProfile(Profile profile) {
        profileDao.update(profile);
    }

    public void deleteProfile(Profile profile) {
        profileDao.delete(profile);
    }
}