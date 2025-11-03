package COMP2450.model;


import COMP2450.model.Media.MediaInterface;

/**
 * Review
 * A user-written review of a media item, storing the reviewer, comment text,
 * and a star rating to capture feedback.
 */

public record Review(User user, MediaInterface media, String comment, int stars) {
}