package concuroo.types;

import concuroo.nodes.Node;
import concuroo.nodes.Expression;
import concuroo.types.ReturnType;
import concuroo.types.TypeModifier;
import concuroo.types.Types;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class TypeRules {

  public static ReturnType determineArithmeticReturnType(Node firstNode, Node secondNode) {
    ReturnType firstReturnType = TypeRules.determineIfValidType(firstNode, Types.DOUBLE, Types.INT, Types.CHAR);
    ReturnType secondReturnType = TypeRules.determineIfValidType(secondNode, Types.DOUBLE, Types.INT, Types.CHAR);

    return TypeRules.determineReturnType(firstReturnType, secondReturnType);
  }

  public static void determineBooleanReturnType(Node firstNode, Node secondNode) {
    ReturnType firstReturnType = TypeRules.determineIfValidType(firstNode, Types.BOOL);
    ReturnType secondReturnType = TypeRules.determineIfValidType(secondNode, Types.BOOL);

    TypeRules.determineReturnType(firstReturnType, secondReturnType);
  }

  public static ReturnType throwIfInvalidDeclaration(ReturnType declReturnType, Node firstNode) {
    if (declReturnType.type == Types.BOOL) {
      return TypeRules.determineIfValidType(firstNode, Types.BOOL);
    } else {
      return TypeRules.determineIfValidType(firstNode, Types.DOUBLE, Types.INT, Types.CHAR);
    }
  }

  public static ReturnType determineIfValidType(Node node, Types... allowedTypes) {
    ReturnType nodeReturnType = ((Expression) node).getReturnType();
    for (Types type : allowedTypes) {
      if (nodeReturnType.type == type) {
        return nodeReturnType;
      }
    }

    StringBuilder allowedTypesString = new StringBuilder();
    for (Types type : allowedTypes) {
      allowedTypesString.append(type.toString());
      allowedTypesString.append(", ");
    }

    throw new RuntimeException(
        "Invalid type. Expected: " + allowedTypesString + " but it wasn't found.");
  }

  public static ReturnType determineDeclarationSpecifier(List<String> specifiers) {
    ReturnType returnType = new ReturnType();

    switch (specifiers.size()) {
      case 0:
        throw new RuntimeException("No specifiers found");
      case 3:
        returnType.prefix = Pair.of(returnType.prefix.getLeft(), modifierToType(specifiers.get(1)));
      case 2:
        returnType.prefix = Pair.of(modifierToType(specifiers.get(0)), returnType.prefix.getRight());

        // Ensures that long chan and long long are not used
        if (returnType.prefix.getLeft() == TypeModifier.LONG
            && returnType.prefix.getRight() == TypeModifier.CHAN) {
          throw new RuntimeException("Undefined behaviour with modifiers: long chan");
        } else if (returnType.prefix.getLeft() == TypeModifier.LONG
            && returnType.prefix.getRight() == TypeModifier.LONG) {
          throw new RuntimeException("Undefined behaviour with modifiers: long long");
        }
      case 1:
        returnType.type = specifierToType(specifiers.get(specifiers.size() - 1));

        // Ensures that long bool and chan long bool are not used
        if (returnType.type == Types.BOOL) {
          if (returnType.prefix.getLeft() == TypeModifier.LONG) {
            throw new RuntimeException("Undefined behaviour: long bool");
          } else if(returnType.prefix.getRight() == TypeModifier.LONG) {
            throw new RuntimeException("Undefined behaviour: chan long bool");
          }
        }
        break;
      default:
        throw new RuntimeException(
            "Too many specifiers in: " + StringUtils.join(specifiers, " ") +
                ". Maximum is 3."
        );
    }

    return returnType;
  }

  private static Types specifierToType(String specifier) {
    switch (specifier) {
      case "double":
        return Types.DOUBLE;
      case "int":
        return Types.INT;
      case "char":
        return Types.CHAR;
      case "bool":
        return Types.BOOL;
      case "void":
        return Types.VOID;
      default:
        throw new RuntimeException("Unrecognized type: " + specifier);
    }
  }

  private static TypeModifier modifierToType(String modifier) {
    switch (modifier) {
      case "long":
        return TypeModifier.LONG;
      case "chan":
        return TypeModifier.CHAN;
      default:
        throw new RuntimeException("Unrecognized modifier: " + modifier);
    }
  }

  private static ReturnType determineReturnType(
      ReturnType assignmentReturnType, ReturnType assigneeReturnType) {
    ReturnType returnType = new ReturnType();

    if (assignmentReturnType.type == Types.DOUBLE || assigneeReturnType.type == Types.DOUBLE) {
      returnType.type = Types.DOUBLE;
    } else if (assignmentReturnType.type == Types.INT || assigneeReturnType.type == Types.INT) {
      returnType.type = Types.INT;
    } else if (assignmentReturnType.type == Types.CHAR || assigneeReturnType.type == Types.CHAR) {
      returnType.type = Types.CHAR;
    } else if (assignmentReturnType.type == Types.BOOL || assigneeReturnType.type == Types.BOOL) {
      returnType.type = Types.BOOL;
    } else {
      returnType.type = Types.VOID;
    }

    return returnType;
  }

  public static boolean isPrecedenceCorrect(
      ReturnType assignmentReturnType, ReturnType assigneeReturnType) {
    return assignmentReturnType.getPrecedenceLevel() >= assigneeReturnType.getPrecedenceLevel();
  }
}

