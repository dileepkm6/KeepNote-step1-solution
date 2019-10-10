
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.table, th, .td {
  border: 1px solid black;
}
input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

input[type="number"] {
    -moz-appearance: textfield;
}
div
{
margin:10px;
}
</style>
<title>KeepNote</title>
</head>
<body>
<h1>Note Information..</h1>
	<!-- Create a form which will have text boxes for Note ID, title, content and status along with a Send 
		 button. Handle errors like empty fields -->
		        <form action="saveNote" method="post">
                            <table>
                                <tr>
                                    <td><label for="noteId">NoteID</label></td>
                                    <td><input  type="number" name="noteId" placeholder="noteId" id="noteId" required></td>
                                </tr>
                                <tr>
                                    <td><label for="noteTitle">NoteTitle</label></td>
                                    <td><input type="text" name="noteTitle" placeholder="noteTitle" id="noteTitle" required></td>
                                </tr>
                                <tr>
                                    <td><label for="noteContent">NoteContent</label></td>
                                    <td><input type="text" name="noteContent" placeholder="noteContent" id="noteContent" required></td>
                                </tr>
                                <tr>
                                    <td><label for="noteStatus">NoteStatus</label></td>
                                    <td><input type="text" name="noteStatus" placeholder="noteStatus" id="noteStatus" required></br></td>
                                </tr>
                                <tr>
                                    <td><input type="submit" value="submit"></td>
                                </tr>
                        </table>
                       </form>
                 <div>
                 <form action="deleteNote" method="get" >
                 <input type="number" name="noteId" placeholder="enter noteId"></br>
                 <input type="submit" value="deleteNote">
                 </form>
                 </div>
	        <table class="table">
                <tr>
                    <th>NoteId</th>
                    <th>NoteTitle</th>
                    <th>NoteContent</th>
                    <th>NoteStatus</th>
                    <th>CreatedAt</th>
                </tr>
                <c:forEach items="${noteList}" var="notes">
                <tr>
                    <td class="td">${notes.getNoteId()}</td>
                    <td class="td">${notes.getNoteTitle()}</td>
                    <td class="td">${notes.getNoteContent()}</td>
                    <td class="td">${notes.getNoteStatus()}</td>
                    <td class="td">${notes.getCreatedAt()}</td>
                </tr>
                </c:forEach>
            </table>
</body>
</html>