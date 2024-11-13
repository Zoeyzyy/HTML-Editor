package command;

/**
 * Command creator interface
 */
@FunctionalInterface
public interface CommandCreator {
    Command create(String ...args);
}
