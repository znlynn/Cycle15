package com.daelabs.workout;

/**
 * Created by Owner on 11/29/2015.
 */
public class Workout {
    private String name;
    private String description;

    public static final Workout[] workouts = {
            new Workout("The Limb Loosener",
                    "5 Handstand Pushups\n10 Single Legged Squats\n15 Pullups"),
            new Workout("Core Agony",
                    "100 Pullups\n100 Pushups\n100 Situps\n100 Squats"),
            new Workout("The Wimp Special",
                    "5 Pullups\n10 Pushups\n15 Squats"),
            new Workout("Strength and Length",
                    "500 Meter Run\n21 x 1.5 Pood Kettlebell Swing\n21 Pushups"),
    };

    private Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return this.name;
    }
}
