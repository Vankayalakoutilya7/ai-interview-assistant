import os
from flask import Flask, request, jsonify
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

app = Flask(__name__)


def round(param, param1):
    pass


@app.route('/evaluate', methods=['POST'])
def evaluate():
    data = request.json

    user_answer = data.get("userAnswer", "")
    model_answer = data.get("modelAnswer", "")

    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform([user_answer, model_answer])

    similarity = cosine_similarity(vectors[0], vectors[1])[0][0]

    return jsonify({
        "similarityScore": round(similarity * 100, 2)
    })


def int(param):
    pass


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 5000)))