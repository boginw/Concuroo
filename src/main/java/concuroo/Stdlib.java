package concuroo;

import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.expression.Identifier;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Arduino standard library. This class ensures that the available methods from the Arduino
 * stdlib are available to the user.
 */
public class Stdlib {

  /**
   * This method returns a method from the Arduino Standard Library, namely: void pinMode(uint8_t
   * pin, uint8_t mode);
   *
   * @return Identifier
   */
  public static Identifier pinMode() {
    FunctionDeclaration pinMode = getFuncDef("pinMode", getVoidSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());

    VariableDeclaration mode = getVarDef("mode", getIntSpecifier());

    pinMode.add(pin);
    pinMode.add(mode);

    return pinMode;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: int digitalRead(uint8_t
   * pin);
   *
   * @return Identifier
   */
  public static Identifier digitalRead() {
    FunctionDeclaration digitalRead = getFuncDef("digitalRead", getIntSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());

    digitalRead.add(pin);

    return digitalRead;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void
   * digitalWrite(uint8_t pin, uint8_t val);
   *
   * @return Identifier
   */
  public static Identifier digitalWrite() {
    FunctionDeclaration func = getFuncDef("digitalWrite", getVoidSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());

    VariableDeclaration value = getVarDef("value", getIntSpecifier());

    func.add(pin);
    func.add(value);

    return func;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: int analogRead(uint8_t
   * pin);
   *
   * @return Identifier
   */
  public static Identifier analogRead() {
    FunctionDeclaration func = getFuncDef("analogRead", getIntSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());

    func.add(pin);

    return func;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void
   * analogReference(uint8_t mode);
   *
   * @return Identifier
   */
  public static Identifier analogReference() {
    FunctionDeclaration func = getFuncDef("analogReference", getVoidSpecifier());

    VariableDeclaration mode = getVarDef("mode", getIntSpecifier());

    func.add(mode);

    return func;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void
   * analogWrite(uint8_t pin, int val);
   *
   * @return Identifier
   */
  public static Identifier analogWrite() {
    FunctionDeclaration func = getFuncDef("analogWrite", getVoidSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());
    VariableDeclaration val = getVarDef("val", getIntSpecifier());

    func.add(pin);
    func.add(val);

    return func;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void noTone(uint8_t
   * _pin);
   *
   * @return Identifier
   */
  public static Identifier noTone() {
    FunctionDeclaration noTone = getFuncDef("noTone", getVoidSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());

    noTone.add(pin);

    return noTone;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: unsigned long
   * pulseIn(uint8_t pin, uint8_t state, unsigned long timeout);
   *
   * @return Identifier
   */
  public static Identifier pulseIn() {
    FunctionDeclaration pulseIn = getFuncDef("pulseIn", getLongIntSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());
    VariableDeclaration state = getVarDef("state", getIntSpecifier());
    VariableDeclaration timeout = getVarDef("timeout", getLongIntSpecifier());

    pulseIn.add(pin);
    pulseIn.add(state);
    pulseIn.add(timeout);

    return pulseIn;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: unsigned long
   * pulseInLong(uint8_t pin, uint8_t state, unsigned long timeout);
   *
   * @return Identifier
   */
  public static Identifier pulseInLong() {
    FunctionDeclaration pulseInLong = getFuncDef("pulseInLong", getLongIntSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());
    VariableDeclaration state = getVarDef("state", getIntSpecifier());
    VariableDeclaration timeout = getVarDef("timeout", getLongIntSpecifier());

    pulseInLong.add(pin);
    pulseInLong.add(state);
    pulseInLong.add(timeout);

    return pulseInLong;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: uint8_t
   * shiftIn(uint8_t* dataPin, uint8_t clockPin, uint8_t bitOrder);
   *
   * @return Identifier
   */
  public static Identifier shiftIn() {
    FunctionDeclaration shiftIn = getFuncDef("shiftIn", getIntSpecifier());

    VariableDeclaration dataPin = getVarDef("dataPin", getIntSpecifier());
    VariableDeclaration clockPin = getVarDef("clockPin", getIntSpecifier());
    VariableDeclaration bitOrder = getVarDef("bitOrder", getIntSpecifier());

    shiftIn.add(dataPin);
    shiftIn.add(clockPin);
    shiftIn.add(bitOrder);

    return shiftIn;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void shiftOut(uint8_t
   * dataPin, uint8_t clockPin, uint8_t bitOrder, uint8_t val);
   *
   * @return Identifier
   */
  public static Identifier shiftOut() {
    FunctionDeclaration shiftOut = getFuncDef("shiftOut", getIntSpecifier());

    VariableDeclaration dataPin = getVarDef("dataPin", getIntSpecifier());
    VariableDeclaration clockPin = getVarDef("clockPin", getIntSpecifier());
    VariableDeclaration bitOrder = getVarDef("bitOrder", getIntSpecifier());
    VariableDeclaration val = getVarDef("val", getIntSpecifier());

    shiftOut.add(dataPin);
    shiftOut.add(clockPin);
    shiftOut.add(bitOrder);
    shiftOut.add(val);

    return shiftOut;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void tone(uint8_t _pin,
   * unsigned int frequency, unsigned long duration = 0);
   *
   * @return Identifier
   */
  public static Identifier tone() {
    FunctionDeclaration tone = getFuncDef("tone", getVoidSpecifier());

    VariableDeclaration pin = getVarDef("pin", getIntSpecifier());
    VariableDeclaration frequency = getVarDef("frequency", getIntSpecifier());
    VariableDeclaration duration = getVarDef("duration", getLongIntSpecifier());

    tone.add(pin);
    tone.add(frequency);
    tone.add(duration);

    return tone;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void delay(unsigned
   * long);
   *
   * @return Identifier
   */
  public static Identifier delay() {
    FunctionDeclaration delay = getFuncDef("delay", getVoidSpecifier());

    VariableDeclaration val = getVarDef("val", getLongIntSpecifier());

    delay.add(val);

    return delay;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: void
   * delayMicroseconds(unsigned int us);
   *
   * @return Identifier
   */
  public static Identifier delayMicroseconds() {
    FunctionDeclaration delayMicroseconds = getFuncDef("delayMicroseconds", getVoidSpecifier());

    VariableDeclaration val = getVarDef("val", getLongIntSpecifier());

    delayMicroseconds.add(val);

    return delayMicroseconds;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: unsigned long
   * micros(void);
   *
   * @return Identifier
   */
  public static Identifier micros() {

    return getFuncDef("micros", getLongIntSpecifier());
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: unsigned long
   * millis(void);
   *
   * @return Identifier
   */
  public static Identifier millis() {
    FunctionDeclaration millis = getFuncDef("millis", getLongIntSpecifier());

    return millis;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: #define abs(x)
   * ((x)>0?(x):-(x))
   *
   * @return Identifier
   */
  public static Identifier abs() {
    FunctionDeclaration abs = getFuncDef("abs", getLongIntSpecifier());

    VariableDeclaration x = getVarDef("x", getLongIntSpecifier());

    abs.add(x);

    return abs;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: #define
   * constrain(amt,low,high) ((amt)<(low)?(low):((amt)>(high)?(high):(amt)))
   *
   * @return Identifier
   */
  public static Identifier constrain() {
    FunctionDeclaration constrain = getFuncDef("constrain", getLongIntSpecifier());

    VariableDeclaration x = getVarDef("x", getLongIntSpecifier());
    VariableDeclaration y = getVarDef("y", getLongIntSpecifier());
    VariableDeclaration z = getVarDef("z", getLongIntSpecifier());

    constrain.add(x);
    constrain.add(y);
    constrain.add(z);

    return constrain;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: long map(long, long,
   * long, long, long);
   *
   * @return Identifier
   */
  public static Identifier map() {
    FunctionDeclaration map = getFuncDef("map", getLongIntSpecifier());

    VariableDeclaration a = getVarDef("a", getLongIntSpecifier());
    VariableDeclaration b = getVarDef("b", getLongIntSpecifier());
    VariableDeclaration c = getVarDef("c", getLongIntSpecifier());
    VariableDeclaration d = getVarDef("d", getLongIntSpecifier());
    VariableDeclaration e = getVarDef("e", getLongIntSpecifier());

    map.add(a);
    map.add(b);
    map.add(c);
    map.add(d);
    map.add(e);

    return map;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: #define _max(a,b)
   * ((a)>(b)?(a):(b))
   *
   * @return Identifier
   */
  public static Identifier max() {
    FunctionDeclaration max = getFuncDef("max", getLongIntSpecifier());

    VariableDeclaration a = getVarDef("a", getLongIntSpecifier());
    VariableDeclaration b = getVarDef("b", getLongIntSpecifier());

    max.add(a);
    max.add(b);

    return max;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: #define _min(a,b)
   * ((a)<(b)?(a):(b))
   *
   * @return Identifier
   */
  public static Identifier min() {
    FunctionDeclaration min = getFuncDef("min", getLongIntSpecifier());

    VariableDeclaration a = getVarDef("a", getLongIntSpecifier());
    VariableDeclaration b = getVarDef("b", getLongIntSpecifier());

    min.add(a);
    min.add(b);

    return min;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: pow - not present in
   * header file
   *
   * @return Identifier
   */
  public static Identifier pow() {
    FunctionDeclaration pow = getFuncDef("pow", getDoubleSpecifier());

    VariableDeclaration base = getVarDef("base", getDoubleSpecifier());
    VariableDeclaration exponent = getVarDef("exponent", getDoubleSpecifier());

    pow.add(base);
    pow.add(exponent);

    return pow;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: #define sq(x)
   * ((x)*(x))
   *
   * @return Identifier
   */
  public static Identifier sq() {
    FunctionDeclaration sq = getFuncDef("sq", getDoubleSpecifier());

    VariableDeclaration x = getVarDef("x", getDoubleSpecifier());

    sq.add(x);

    return sq;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: sqrt - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier sqrt() {
    FunctionDeclaration sqrt = getFuncDef("sqrt", getDoubleSpecifier());

    VariableDeclaration x = getVarDef("x", getDoubleSpecifier());

    sqrt.add(x);

    return sqrt;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: cos - not in the header
   * file
   *
   * @return Identifier
   */
  public static Identifier cos() {
    FunctionDeclaration cos = getFuncDef("cos", getDoubleSpecifier());

    VariableDeclaration rad = getVarDef("rad", getDoubleSpecifier());

    cos.add(rad);

    return cos;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: sin - not in the header
   * file
   *
   * @return Identifier
   */
  public static Identifier sin() {
    FunctionDeclaration sin = getFuncDef("sin", getDoubleSpecifier());

    VariableDeclaration rad = getVarDef("rad", getDoubleSpecifier());

    sin.add(rad);

    return sin;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: tan - not in the header
   * file
   *
   * @return Identifier
   */
  public static Identifier tan() {
    FunctionDeclaration tan = getFuncDef("tan", getDoubleSpecifier());

    VariableDeclaration rad = getVarDef("rad", getDoubleSpecifier());

    tan.add(rad);

    return tan;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isAlpha() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier isAlpha() {
    FunctionDeclaration isAlpha = getFuncDef("isAlpha", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isAlpha.add(thisChar);

    return isAlpha;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isAlphaNumeric() - not
   * in the header file
   *
   * @return Identifier
   */
  public static Identifier isAlphaNumeric() {
    FunctionDeclaration isAlphaNumeric = getFuncDef("isAlphaNumeric", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isAlphaNumeric.add(thisChar);

    return isAlphaNumeric;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isAscii() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier isAscii() {
    FunctionDeclaration isAscii = getFuncDef("isAscii", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isAscii.add(thisChar);

    return isAscii;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isControl() - not in
   * the header file
   *
   * @return Identifier
   */
  public static Identifier isControl() {
    FunctionDeclaration isControl = getFuncDef("isControl", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isControl.add(thisChar);

    return isControl;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isDigit() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier isDigit() {
    FunctionDeclaration isDigit = getFuncDef("isDigit", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isDigit.add(thisChar);

    return isDigit;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isGraph() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier isGraph() {
    FunctionDeclaration isGraph = getFuncDef("isGraph", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isGraph.add(thisChar);

    return isGraph;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isHexadecimalDigit() -
   * not in the header file
   *
   * @return Identifier
   */
  public static Identifier isHexadecimalDigit() {
    FunctionDeclaration isHexadecimalDigit = getFuncDef("isHexadecimalDigit", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isHexadecimalDigit.add(thisChar);

    return isHexadecimalDigit;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isLowerCase() - not in
   * the header file
   *
   * @return Identifier
   */
  public static Identifier isLowerCase() {
    FunctionDeclaration isLowerCase = getFuncDef("isLowerCase", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isLowerCase.add(thisChar);

    return isLowerCase;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isPrintable() - not in
   * the header file
   *
   * @return Identifier
   */
  public static Identifier isPrintable() {
    FunctionDeclaration isPrintable = getFuncDef("isPrintable", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isPrintable.add(thisChar);

    return isPrintable;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isPunct() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier isPunct() {
    FunctionDeclaration isPunct = getFuncDef("isPunct", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isPunct.add(thisChar);

    return isPunct;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isSpace() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier isSpace() {
    FunctionDeclaration isSpace = getFuncDef("isSpace", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isSpace.add(thisChar);

    return isSpace;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isUpperCase() - not in
   * the header file
   *
   * @return Identifier
   */
  public static Identifier isUpperCase() {
    FunctionDeclaration isUpperCase = getFuncDef("isUpperCase", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isUpperCase.add(thisChar);

    return isUpperCase;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: isWhitespace() - not in
   * the header file
   *
   * @return Identifier
   */
  public static Identifier isWhitespace() {
    FunctionDeclaration isWhitespace = getFuncDef("isWhitespace", getBoolSpecifier());

    VariableDeclaration thisChar = getVarDef("thisChar", getCharSpecifier());

    isWhitespace.add(thisChar);

    return isWhitespace;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: random() - not in the
   * header file
   *
   * @return Identifier
   */
  public static Identifier random() {
    FunctionDeclaration random = getFuncDef("random", getLongIntSpecifier());

    VariableDeclaration min = getVarDef("min", getLongIntSpecifier());
    VariableDeclaration max = getVarDef("max", getLongIntSpecifier());

    random.add(min);
    random.add(max);

    return random;
  }

  /**
   * This method returns a method from the Arduino Standard Library, namely: randomSeed() - not in
   * the header file
   *
   * @return Identifier
   */
  public static Identifier randomSeed() {
    FunctionDeclaration randomSeed = getFuncDef("randomSeed", getLongIntSpecifier());

    VariableDeclaration seed = getVarDef("seed", getLongIntSpecifier());

    randomSeed.add(seed);

    return randomSeed;
  }

  /**
   * adds the entire stdlib to the given symbol table
   *
   * @param st Symbol Table to add to
   */
  public static void addTo(SymbolTable st) {
    st.insert(pinMode());
    st.insert(digitalRead());
    st.insert(digitalWrite());
    st.insert(analogRead());
    st.insert(analogReference());
    st.insert(analogWrite());
    st.insert(noTone());
    st.insert(pulseIn());
    st.insert(pulseInLong());
    st.insert(shiftIn());
    st.insert(shiftOut());
    st.insert(tone());
    st.insert(delay());
    st.insert(delayMicroseconds());
    st.insert(micros());
    st.insert(millis());
    st.insert(abs());
    st.insert(constrain());
    st.insert(map());
    st.insert(max());
    st.insert(min());
    st.insert(pow());
    st.insert(sq());
    st.insert(sqrt());
    st.insert(cos());
    st.insert(sin());
    st.insert(tan());
    st.insert(isAlpha());
    st.insert(isAlphaNumeric());
    st.insert(isAscii());
    st.insert(isControl());
    st.insert(isDigit());
    st.insert(isGraph());
    st.insert(isHexadecimalDigit());
    st.insert(isLowerCase());
    st.insert(isPrintable());
    st.insert(isPunct());
    st.insert(isSpace());
    st.insert(isUpperCase());
    st.insert(isWhitespace());
    st.insert(random());
    st.insert(randomSeed());
  }

  /**
   * Searches a string, to find whether it contains a stdlib method
   *
   * @param search The string to find
   * @return Whether or not it contains a stdlib method
   */
  public static boolean in(String search) {
    List<String> stdlib = new ArrayList<String>() {{
      add("pinMode");
      add("digitalRead");
      add("digitalWrite");
      add("analogRead");
      add("analogReference");
      add("analogWrite");
      add("noTone");
      add("pulseIn");
      add("pulseInLong");
      add("shiftIn");
      add("shiftOut");
      add("tone");
      add("delay");
      add("delayMicroseconds");
      add("micros");
      add("millis");
      add("abs");
      add("constrain");
      add("map");
      add("max");
      add("min");
      add("pow");
      add("sq");
      add("sqrt");
      add("cos");
      add("sin");
      add("tan");
      add("isAlpha");
      add("isAlphaNumeric");
      add("isAscii");
      add("isControl");
      add("isDigit");
      add("isGraph");
      add("isHexadecimalDigit");
      add("isLowerCase");
      add("isPrintable");
      add("isPunct");
      add("isSpace");
      add("isUpperCase");
      add("isWhitespace");
      add("random");
      add("randomSeed");
    }};

    return stdlib.contains(search);
  }

  /*
   *** HELPERS
   */

  private static FunctionDeclaration getFuncDef(String Identifier, DeclarationSpecifierList decl) {
    FunctionDeclaration func = new FunctionDeclaration();
    func.setIdentifier(Identifier);
    func.setSpecifiers(decl);

    return func;
  }

  private static VariableDeclaration getVarDef(String Identifier, DeclarationSpecifierList decl) {
    VariableDeclaration var = new VariableDeclaration();
    var.setIdentifier(Identifier);
    var.setSpecifiers(decl);

    return var;
  }

  private static DeclarationSpecifierList getIntSpecifier() {
    return new DeclarationSpecifierList(new ArrayList<String>() {{
      add("int");
    }});
  }

  private static DeclarationSpecifierList getBoolSpecifier() {
    return new DeclarationSpecifierList(new ArrayList<String>() {{
      add("bool");
    }});
  }

  private static DeclarationSpecifierList getCharSpecifier() {
    return new DeclarationSpecifierList(new ArrayList<String>() {{
      add("char");
    }});
  }

  private static DeclarationSpecifierList getLongIntSpecifier() {
    return new DeclarationSpecifierList(new ArrayList<String>() {{
      add("long");
      add("int");
    }});
  }


  private static DeclarationSpecifierList getVoidSpecifier() {
    return new DeclarationSpecifierList(new ArrayList<String>() {{
      add("void");
    }});
  }

  private static DeclarationSpecifierList getDoubleSpecifier() {
    return new DeclarationSpecifierList(new ArrayList<String>() {{
      add("double");
    }});
  }


}
