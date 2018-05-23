package concurooInterface;

public interface Pipe extends Step {
  Pipe pipe(String program);
  String build();
  void out(Out output, String path);
}
