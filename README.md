Run the URL Shortener Project (Java + Maven + HTML/JS)
bash
Copy
Edit

# 1. Clone the repo
git clone https://github.com/aditya1996gupta/url-shortener.git
cd url-shortener

# 2. Build the project
mvn clean install

# 3. Run the backend server (Java HTTPServer on port 8081)
mvn exec:java -Dexec.mainClass="com.urlshortener.Main"

# -------------------------------
# Open another terminal for frontend:
# -------------------------------

# 4. Serve frontend via Python (if Python is installed)
cd web
python -m http.server 8000

# 5. Then visit this in your browser:
# http://localhost:8000
🔗 Sample Test Flow:
Open: http://localhost:8000

Input long URL like: https://www.google.com

Click "Shorten"

You'll receive a short URL like: http://localhost:8081/u/abc123

Open that short URL — it redirects to the original.
