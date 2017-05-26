package com.project.movie.mymovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.List;

public class MovieModel implements Comparator<MovieModel>, Parcelable {


    private int id;
    private String title, releaseDate, posterPath, character, departmentAndJob, mediaType,
            original_title, original_language, release_date;

    private String poster_path;
    private String backdrop_path;
    @SerializedName("overview")
    private String description;



    public MovieModel() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        try {
            this.releaseDate = releaseDate.substring(0, 4);
        } catch (StringIndexOutOfBoundsException e) {
            this.releaseDate = null;
        }
    }

    public String getOriginal_title() {return original_title;}

    public void setOriginal_title(String original_title) {this.original_title = original_title;}

    public String getOriginal_language() {return original_language;}

    public void setOriginal_language(String original_language) {this.original_language = original_language;}

    public String getRelease_date() {return release_date;}

    public void setRelease_date(String release_date) {this.release_date = release_date;}

    public String getBackdrop() {return "http://image.tmdb.org/t/p/w500"  + backdrop_path;}

    public void setBackdrop(String backdrop) {this.backdrop_path = backdrop;}

    public String getPoster() {return "http://image.tmdb.org/t/p/w500" + poster_path;}

    public void setPoster(String poster) {this.poster_path = poster;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {return "http://image.tmdb.org/t/p/w500" + posterPath;}

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getDepartmentAndJob() {
        return this.departmentAndJob;
    }

    public void setDepartmentAndJob(String departmentAndJob) {
        this.departmentAndJob = departmentAndJob;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public int compare(MovieModel movie1, MovieModel movie2) {
        String year1, year2;
        int compareYear1, compareYear2;

        try {
            year1 = movie1.getReleaseDate();
        } catch (NullPointerException e) {
            year1 = "0";
        }

        try {
            year2 = movie2.getReleaseDate();
        } catch (NullPointerException e) {
            year2 = "0";
        }


        try {
            compareYear1 = Integer.parseInt(year1);
        } catch (NumberFormatException e) {
            compareYear1 = 0;
        }


        try {
            compareYear2 = Integer.parseInt(year2);
        } catch (NumberFormatException e) {
            compareYear2 = 0;
        }

        if (compareYear1 == compareYear2)
            return 0;

        if (compareYear1 < compareYear2)
            return 1;
        else return -1;
    }


    protected MovieModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        poster_path = in.readString();
        character = in.readString();
        departmentAndJob = in.readString();
        mediaType = in.readString();
        backdrop_path = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(description);
        dest.writeString(posterPath);
        dest.writeString(character);
        dest.writeString(departmentAndJob);
        dest.writeString(mediaType);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public static class MovieResult {
        private List<MovieModel> results;
        public List<MovieModel> getResults() {
            return results;
        }
    }
}