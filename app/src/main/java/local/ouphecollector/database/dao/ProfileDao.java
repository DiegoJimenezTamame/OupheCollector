package local.ouphecollector.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import local.ouphecollector.models.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Insert
    void insert(Profile profile);

    @Update
    void update(Profile profile);

    @Delete
    void delete(Profile profile);

    @Query("SELECT * FROM Profile")
    List<Profile> getAllProfiles();

    @Query("SELECT * FROM Profile WHERE id = :profileId")
    Profile getProfileById(int profileId);
}