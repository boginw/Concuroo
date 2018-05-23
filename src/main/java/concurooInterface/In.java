package concurooInterface;

public interface In extends Step {
  In input(String path);
  Pipe to(Pipe pipe);
}
