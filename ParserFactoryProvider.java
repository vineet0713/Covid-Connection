public class ParserFactoryProvider {

  public static ParserFactory getFactory(String fileType) throws Exception {
    switch (fileType) {
      case "plaintext":
        return PlaintextParserFactory.getInstance();
      case "db":
        return DbParserFactory.getInstance();
      default:
        throw new Exception("Unsupported data format: " + fileType);
    }
  }
}
