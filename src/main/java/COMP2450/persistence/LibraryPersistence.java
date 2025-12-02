package COMP2450.persistence;
import COMP2450.domain.Library;
import COMP2450.persistence.json.NotFoundException;

public interface LibraryPersistence {

    /**
     * Persist this {@link Library}.
     *
     * @param library the {@link Library} to save.
     * @return the {@link Library}, maybe modified by the persistence implementation.
     */
    Library save(Library library);

    /**
     * Load a specific {@link Library} by their name.
     *
     * @param name the name of the {@link Library}.
     * @return the {@link Library} with that name.
     * @throws {@link NotFoundException} if no {@link Library} has that name.
     */
    Library load(String name) throws NotFoundException;

}
