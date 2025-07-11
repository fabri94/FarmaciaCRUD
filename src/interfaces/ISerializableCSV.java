package interfaces;

/**
 *
 * @author Fabri
 */
public interface ISerializableCSV {
    String toCSV();
    ISerializableCSV fromCSV(String[] datos);
}
