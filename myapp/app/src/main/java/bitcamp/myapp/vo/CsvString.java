package bitcamp.myapp.vo;

/**
 * caller: CSV 문자열을 받아서 파일에 저장하는 쪽, callee: 도메인 객체 = 값 객체(VO) = 데이터 전송 객체(DTO)
 */
public interface CsvString {
  String toCsvString(); // 해당 객체를 문자열로 변환한다.

  //  void fromCsvString(String str); // 문자열을 해당 객체로 변환한다.
  //  String[] getColumns(); // 해당 객체의 컬럼 이름을 배열로 리턴한다.
  //  String[] getColumnTypes(); // 해당 객체의 컬럼 타입을 배열로 리턴한다.
  //  String[] getColumnValues(); // 해당 객체의 컬럼 값을 배열로 리턴한다.
}
