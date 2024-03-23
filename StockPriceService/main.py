from flask import Flask, request,jsonify
import yfinance as yf
from flask_cors import CORS

def create_app():
    app =Flask(__name__)
    CORS(app)
    @app.route("/stockprice/liveprice")
    def livePrice():
        ticker = request.args.get("ticker")
        ticker_obj = yf.Ticker(ticker.upper()+".BO")
        hist = ticker_obj.history(period="5d")
        print(ticker_obj.info)
        return {"livePrice" : ticker_obj.info['bid'] , "open" : hist.iloc[4]['Open']}

    @app.route("/stockprice/historic")
    def historic():
        ticker = request.args.get("symbol")
        ticker_obj = yf.Ticker(ticker.upper()+".BO")
        data = ticker_obj.history(period="max")
        index = data.index
        output = []
        for i in range(len(index)):
            output.append({"Date" : index[i] , "Open" : float(data['Open'][i]) , "High" : float(data["High"][i]) , "Low" : float(data["Low"][i]) , "Close" : float(data["Close"][i]) , "Volume" : float(data["Volume"][i])})
        return {"Historic Data" : output}
    return app

APP = create_app()
if __name__ == '__main__':
    APP.run(host="localhost" , port=9092)
