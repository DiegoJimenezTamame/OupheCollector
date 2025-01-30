package local.ouphecollector.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import local.ouphecollector.database.AppDatabase;
import local.ouphecollector.database.dao.ProfileDao;
import local.ouphecollector.models.Profile;

import java.util.List;

public class ProfileRepository {
    private ProfileDao profileDao;
    private LiveData<List<Profile>> allProfiles;

    public ProfileRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        profileDao = db.profileDao();
        allProfiles = profileDao.getAllProfiles();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return allProfiles; // Changed to LiveData
    }

    public Profile getProfileById(int profileId) {
        return profileDao.getProfileById(profileId);
    }

    public void insertProfile(Profile profile) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            profileDao.insert(profile);
        });
    }

    public void updateProfile(Profile profile) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            profileDao.update(profile);
        });
    }

    public void deleteProfile(Profile profile) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            profileDao.delete(profile);
        });
    }
}