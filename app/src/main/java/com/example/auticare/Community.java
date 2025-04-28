package com.example.auticare;

public class Community {
    private String name;
    private String description;
    private int iconResId;  // Image/icon for community

    public Community() {
        // Empty constructor required for Firebase (if needed later)
    }

    public Community(String name, String description, int iconResId) {
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }

    // Setters (optional if needed)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
