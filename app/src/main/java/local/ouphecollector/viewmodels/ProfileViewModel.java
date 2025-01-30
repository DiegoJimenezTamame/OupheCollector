package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import local.ouphecollector.models.Profile;
import local.ouphecollector.repositories.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<List<Profile>> allProfiles;

    public ProfileViewModel(Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
        allProfiles = profileRepository.getAllProfiles();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return allProfiles;
    }

    public void insertProfile(Profile profile) {
        profileRepository.insertProfile(profile);
    }

    public void updateProfile(Profile profile) {
        profileRepository.updateProfile(profile);
    }

    public void deleteProfile(Profile profile) {
        profileRepository.deleteProfile(profile);
    }
}