package work;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Fortune
 */
public class Fortune extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Fortune() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int scriptNo = 0;
		String name = null;

		// ******** 結果表示に利用するための値を用意 ********
		int birthMonth = 0;
		int birthDay = 0;

		birthMonth = Integer.parseInt(request.getParameter("user_birth_month"));
		birthDay   = Integer.parseInt(request.getParameter("user_birth_day"));
		name       = request.getParameter("user_name");

		/*
		 * 占い結果No.を取得するための計算式は次の通り
		 *
		 *   占い結果No. = 入力誕生月 + 入力誕生日付 + 名前の文字数 + 今日の日付
		 *
		 *   ※後続処理で1～20の間の数になるよう調整
		 */
		Date date = new Date();
		scriptNo = birthMonth + birthDay + name.length() + date.getDate();

		// 運勢は20個しか用意してないので…
		while (scriptNo > 20) {
			scriptNo = scriptNo - 20;
		}

		// ******** 入力がない場合は適当な値をセット ********
		if (scriptNo == 0) {
			Random rand = new Random();
			scriptNo = rand.nextInt(20) + 1;
		}
		if (name == "") {
			name = "あなた";
		}

		// ******** 占い結果を表示させるためのHTMLを生成 ********
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html>                                           ");
		out.println("<head>                                           ");
		out.println("  <link rel=\"stylesheet\" href=\"fortune.css\"> ");
		out.println("  <meta charset=\"UTF-8\">                       ");
		out.println("  <title>今日の運勢</title>                      ");
		out.println("</head>                                          ");
		out.println("<body>                                           ");

		// ヘッドライン
		out.println("  <h3>今日の " + name + " さんの運勢は…</h3> ");

		// 運勢を表示する
		out.println("  <h1>" + FortuneScripts.fortuneScripts.get(scriptNo) + "</h1>");

		out.println("  </br>                                ");
		out.println("  <hr>                                 ");
		out.println("  <a href=\"input-form.html\">戻る</a> ");
		out.println("</body>                                ");
		out.println("</html>                                ");
	}
}
