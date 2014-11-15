<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="../layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Edit Quiz" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">
			<ul class="uk-navbar-nav ">
				<li><a href="editQuizes.jsp">Home</a></li>
				<li><a href="createUser.jsp">Create Candidate</a></li>
				<li><a href="createQuiz.jsp">Create Quiz</a></li>
				<li><a href="../../CandidateServlet?displayCandidates">View
						Candidates</a></li>
				<li><a href="../../LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<c:if test="${errorM!=null }">
			<div class="uk-alert uk-alert-danger" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${errorM}</p>
			</div>
			<c:remove var="errorMessage" />
		</c:if>
		<c:if test="${notifyM!=null }">
			<div class="uk-alert uk-alert-success" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${notifyM}</p>
			</div>
			<c:remove var="notifyM" scope="session"></c:remove>
		</c:if>
		<br />
		<h1>Edit Quiz</h1>
		<br />
		<form method="post" action="../../EditQuizServlet">
			<div class="uk-form-row">
				<input type="text" name="quizName"
					value="${fn:escapeXml(quiz.getName())}">
			</div>
			<div class="uk-form-row">
				<c:if test="${quiz.getOrdered()==true}">
					<label><input type="checkbox" value=true name="ordered" checked />Ordered?</label>
				</c:if>
				<c:if test="${quiz.getOrdered()==false}">
					<label><input type="checkbox" value=true name="ordered" />Ordered?</label>
				</c:if>
			</div>
			<div class="uk-form-row">
				Time Limit: <input type="number" value="${quiz.getTimeLimit()}"
					name="timeLimit">
			</div>
			<div class="uk-form-row">
				<c:if test="${quiz.getType()==0}">
					<label><input type="radio" value=0 name="type" checked>Single answer</label>
                  <label><input type="radio" value=1 name="type">Multiple answers</label>
                </c:if>
				<c:if test="${quiz.getType()==1}">
					<label><input type="radio" value=0 name="type">Single answer</label>
                  <label><input type="radio" value=1 name="type" checked>Multiple answers</label>
                </c:if>
			</div>
			<div class="uk-form-row">
				Comments: <br />
				<textarea rows="5" cols="50" name="comments">${quiz.getComments()}</textarea>
			</div>
			<br />
			 <input type="hidden" value="${quiz.getId()}" name="quizId">	
			 <input class="uk-button" type="submit" name="submit" value="Update Quiz">
			 <button class="uk-button" type="submit" name="deleteQuiz" value="${quiz.getId()}">Delete Quiz</button>
			
				
				<p>Questions</p>
			<div class="uk-form-row">
				<ol>

					<c:forEach var="question" items="${quiz.getQuestions()}">
						<br />
						<li>Title: ${fn:escapeXml(question.getTitle())}
							<div class="uk-form-row">Text:
								${fn:escapeXml(question.getText())}</div>
							<div class="uk-form-row">
								<c:if test="${question.getOrdered()==false}">
								Ordered Answers: <input type="checkbox" value=true
										name="answersOrdered" />
								</c:if>
								<c:if test="${question.getOrdered()==true }">
								Ordered Answers: <input type="checkbox" value=true
										name="answersOrdered" checked />
								</c:if>
							</div>
							<div class="uk-form-row">
								<ul>
									<c:forEach var="answer" items="${question.getAnswers()}">

										<li>${fn:escapeXml(answer.getText())}
										<c:if test="${answer.isCorrect()==true}">
												<input type="checkbox" value=true checked>Correct
					                     </c:if> 
					                     <c:if test="${answer.isCorrect()==false}">
												<input type="checkbox" value=true>Correct
					                     </c:if>

										</li>

									</c:forEach>
								</ul>

							</div>
							<div class="uk-form-row">
								<button type="submit" class="uk-button" name="editQuestion"
									value="${question.getId()}">Edit Question</button>
								<button type="submit" class="uk-button" name="deleteQuestion"
									value="${question.getId()}">Delete Question</button>
							</div>
						</li>
					</c:forEach>
				</ol>
			</div>
           <button type="submit" class="uk-button" name="addQuestion" value="${quiz.getId()}">Add Question</button>
		</form>

		<c:import url="../layouts/footer.jsp"></c:import>
	</div>