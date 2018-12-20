<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Number Guess Game
Written by Jason Hunter, CTO, K&A Software
http://www.servlets.com
-->

<%@ page import = "controller.NumberGuessBean" %>

<jsp:useBean id="numGuess" class="controller.NumberGuessBean" scope="session"/>
<jsp:setProperty name="numGuess" property="*"/>

<!DOCTYPE html>
<html>
<head>
  <title>Number Guess XXX</title>
</head>
<body>

<% if (numGuess.getSuccess()) { %>

Congratulations!  You got it.
And after just <%= numGuess.getNumGuesses() %> tries.<p>

    <% numGuess.reset(); %>

  Care to <a href="index.jsp">try again</a>?

    <% } else if (numGuess.getNumGuesses() == 0) { %>

  Welcome to the Number Guess game.<p>

  I'm thinking of a number between 1 and 100.<p>

<form method=get>
  What's your guess? <input type=text name=guess>
  <input type=submit value="Submit">
</form>

<% } else { %>

Good guess, but nope.  Try <b><%= numGuess.getHint() %></b>.

You have made <%= numGuess.getNumGuesses() %> guesses.<p>

  I'm thinking of a number between 1 and 100.<p>

<form method=get>
  What's your guess? <input type=text name=guess>
  <input type=submit value="Submit">
</form>

<% } %>

</body>
</html>
