<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<h1>과제</h1>
<a href='/app/assignment/form'>새 글</a>
<table border='1'>
    <thead>
    <tr>
        <th>번호</th>
        <th>과제</th>
        <th>제출마감일</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="assignment">
        <tr>
            <td>${assignment.no}</td>
            <td><a href='/app/assignment/view?no=${assignment.no}'>${assignment.title}</a></td>
            <td>${assignment.deadline}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="map" style="width:500px;height:400px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7b2bcf444fddcc4cd3018a41c520a1fe"></script>
<!-- services와 clusterer, drawing 라이브러리 불러오기 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7b2bcf444fddcc4cd3018a41c520a1fe&libraries=services,clusterer,drawing"></script>
<script src="//code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>

<script>
    var map = {
        item : {},

        // 맵 로드
        load : function(id, options){
            var thisObj = this;
            if (typeof id == 'undefined'){ return false; }

            // 옵션이 없을 경우 기본값 정의
            if(typeof options == 'undefined'){ options = {};}

            // 지도의 옵션 초기화
            if(typeof options.lat =='undefined'){ options.lat = 37.4994078625536;}
            if(typeof options.lng =='undefined'){ options.lng = 127.029037792462;}
            if(typeof options.level =='undefined'){ options.level = 3;}

            // 맵 옵션
            var mapOptions = {
                center: new kakao.maps.LatLng(options.lat, options.lng), // 지도의 중심좌표
                level: options.level // 지도의 확대 레벨
            }

            // 지도 생성
            thisObj.item[id] = new kakao.maps.Map(document.getElementById(id),mapOptions);

            // 마커 생성
            var marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(options.lat, options.lng)
            });

            // 마커를 적용
            marker.setMap(thisObj.item[id]);
        },
    };
    $(document).ready(function(e){
        map.load('map',{});
    });
</script>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
