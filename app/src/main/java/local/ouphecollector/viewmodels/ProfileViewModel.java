package local.ouphecollector.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import local.ouphecollector.models.Profile;
import local.ouphecollector.repository.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<List<Profile>> allProfiles;

    public ProfileViewModel(Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
        allProfiles = new MutableLiveData<>();
        loadAllProfiles();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return allProfiles;
    }

    public void loadAllProfiles() {
        List<Profile> profiles = profileRepository.getAllProfiles();
        ((MutableLiveData<List<Profile>>) allProfiles).setValue(profiles);
    }

    public void insertProfile(Profile profile) {
        profileRepository.insertProfile(profile);
        loadAllProfiles();
    }

    public void updateProfile(Profile profile) {
        profileRepository.updateProfile(profile);
        loadAllProfiles();
    }

    public void deleteProfile(Profile profile) {
        profileRepository.deleteProfile(profile);
        loadAllProfiles();
    }
}