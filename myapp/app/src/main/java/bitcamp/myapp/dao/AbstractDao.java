package bitcamp.myapp.dao;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public abstract class AbstractDao<T> {
  ArrayList<T> list;

  void loadData(String filepath) {


    try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
      StringBuilder sb = new StringBuilder();
      String str;
      while ((str = in.readLine()) != null) {
        sb.append(str);
      }

      // 이 클래스가 다루는 데이터의 클래스 정보를 알아낸다. Gson 이 역직렬화를 할때 필요하다.
      Class<T> dataType = (Class<T>) ((ParameterizedType) this.getClass()  // 이 메소드를 호출한 클래스의 정보
          .getGenericSuperclass())    // AbstractDao 클래스의 정보
          .getActualTypeArguments()[0];  // AbstractDao 에 전달한 제네릭 타입 클래스 정보
      list = (ArrayList<T>) new GsonBuilder().setDateFormat("yyyy-MM-dd").create()
          .fromJson(sb.toString(), TypeToken.getParameterized(ArrayList.class, dataType));

    } catch (Exception e) {
      list = new ArrayList<>();
      throw new DaoException("데이터 로딩 중 오류!", e);
    }
  }

  void saveData(String filepath) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(filepath))) {
      out.write(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(list));
    } catch (Exception e) {
      throw new DaoException("데이터 저장 오류!", e);
    }
  }
}
