import { useState, useEffect } from "react";
import axios from "axios";

function App() {

    const [questions, setQuestions] = useState([]);
    const [questionId, setQuestionId] = useState(1);
    const [answer, setAnswer] = useState("");
    const [token, setToken] = useState("");
    const [history, setHistory] = useState([]);

    // 🔹 Load questions
    useEffect(() => {
        axios.get("http://localhost:8080/questions")
            .then(res => setQuestions(res.data))
            .catch(err => console.log(err));
    }, []);

    // 🔹 Load history when token changes
    useEffect(() => {
        if (token) {
            loadHistory();
        }
    }, [token]);

    const loadHistory = async () => {

        if (!token) return;   // ✅ IMPORTANT

        try {
            const res = await axios.get("http://localhost:8080/evaluate/history", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setHistory(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    // 🔹 Evaluate answer
    const evaluate = async () => {

        if (!token) {
            alert("Please enter JWT token");
            return;
        }

        try {
            await axios.post(
                "http://localhost:8080/evaluate",
                {
                    questionId: questionId,
                    userAnswer: answer
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            await loadHistory();
            setAnswer("");

        } catch (err) {
            alert("Error: " + err.response?.status);
        }
    };

    // 🔹 Clear history
    const clearHistory = async () => {
        try {
            await axios.delete("http://localhost:8080/evaluate/clear", {
                headers: {
                    Authorization: `Bearer ${token}`   // ✅ ADD THIS
                }
            });

            setHistory([]); // clear UI

        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div style={{ padding: "20px", maxWidth: "600px", margin: "auto" }}>
            <h2>AI Interview Chat</h2>

            <input
                placeholder="Enter JWT Token"
                onChange={(e) => setToken(e.target.value.trim())}
                style={{ width: "100%" }}
            />

            <br /><br />

            <select
                value={questionId}
                onChange={(e) => setQuestionId(e.target.value)}
            >
                {questions.map(q => (
                    <option key={q.id} value={q.id}>
                        {q.questionText}
                    </option>
                ))}
            </select>

            <br /><br />

            <div
                style={{
                    border: "1px solid gray",
                    padding: "10px",
                    height: "300px",
                    overflowY: "scroll"
                }}
            >
                {history.map((item) => (
                    <div key={item.id}>
                        <p><b>You:</b> {item.userAnswer}</p>
                        <p><b>AI:</b> Score {item.score} | {item.feedback}</p>
                        <hr />
                    </div>
                ))}
            </div>

            <br />

            <textarea
                rows="3"
                placeholder="Type your answer..."
                value={answer}
                onChange={(e) => setAnswer(e.target.value)}
                style={{ width: "100%" }}
            />

            <br /><br />

            <button onClick={evaluate}>
                Send
            </button>

            <button
                onClick={clearHistory}
                style={{ marginLeft: "10px", backgroundColor: "red", color: "white" }}
            >
                Clear History
            </button>
        </div>
    );
}

export default App;