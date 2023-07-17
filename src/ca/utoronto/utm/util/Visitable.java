package ca.utoronto.utm.util;

import ca.utoronto.utm.util.Visitor;

public interface Visitable {
  public void accept(Visitor visitor);

}