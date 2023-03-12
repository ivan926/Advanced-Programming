package Service;

public enum Gender {
    MALE("m"),
    FEMALE("f");

    private final String gen;

    Gender(String gender) {
        this.gen = gender;
    }

    @Override
    public String toString() {
        return this.gen;
    }
}
