import os

import pandas
from mysql.connector import connect
from flask import Flask, request,jsonify
from flask_cors import CORS
from dotenv import load_dotenv

load_dotenv()

MYSQL_USERNAME = os.getenv("MYSQL_USERNAME")
MYSQL_PASSWORD = os.getenv("MYSQL_PASSWORD")
MYSQL_DB = os.getenv("MYSQL_DB")

connection = ""
def connect_mysql():
    connection = connect(
        host="localhost",
        user=MYSQL_USERNAME,
        password=MYSQL_PASSWORD,
        database=MYSQL_DB
    )
    return connection
def create_app(testConfig=None):
    app = Flask(__name__)
    CORS(app)
    @app.route("/stockinfo/ticker/refresh")
    def refreshStockTickers():
        df = pandas.read_csv("resources/tradilearn_stocks.csv")
        counter = 0
        for i in df.index:
            name = df["Security Name"][i]
            if type(name) != str:
                continue
            name = name.upper()
            sector = df["Sector Name"][i]
            if type(sector) != str:
                continue
            sector = sector.upper()
            symbol = df['Ticker'][i]
            print(counter , symbol)
            counter+=1
            cursor = connection.cursor();
            statement = "INSERT INTO Stock(ticker , security_name , sector) VALUES (%s , %s, %s)"
            cursor.execute(statement , (symbol , name , sector))
        connection.commit()
        return "Data updated succesfully"

    @app.route("/stockinfo/search")
    def searchStocks():
        cursor = connection.cursor();
        name = request.args.get("stockName")
        name = name.upper()
        arg = ["%"+name+"%"]
        statement = "SELECT * FROM Stock WHERE security_name LIKE %s"
        cursor.execute(statement , arg)
        result = cursor.fetchall()

        # Convert result to a list of dictionaries
        data = [{"Ticker": row[0], "SecurityName": row[1] , "Sector" : row[2]} for row in result]

        response = {
            "stocks": data
        }
        return jsonify(response)
    return app

APP = create_app()
if __name__ == '__main__':
    connection = connect_mysql()
    APP.run(host="localhost" , port=9091)
