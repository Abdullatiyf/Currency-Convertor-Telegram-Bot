<!DOCTYPE html>
<html>
<body>
	<h1>Documentation for Currency Converter Telegram Bot</h1>
	<p>The Currency Converter Telegram Bot is a Java program that allows users to convert between 75 different currencies via a Telegram bot. This bot uses the <a href="https://core.telegram.org/bots/api">Telegram Bot API</a> and the <a href="https://cbu.uz/oz/arkhiv-kursov-valyut/json/">CBU.uz API</a> to fetch the latest currency exchange rates and convert between different currencies.</p>
	<h2>Setup</h2>
	<p>To use this Telegram bot, you will need to perform the following steps:</p>
	<ol>
		<li>Clone the repository by running the following command:</li>
	</ol>
	<pre><code>git clone https://github.com/Abdullatiyf/Currency-Converter-Telegram-Bot.git</code></pre>
	<ol start="2">
		<li>Create a new Telegram bot by following the instructions in the <a href="https://core.telegram.org/bots#creating-a-new-bot">Telegram Bot API documentation</a>. Note down the API token that is provided after the bot is created.</li>
		<li>Register for an account on the <a href="https://cbu.uz/oz/arkhiv-kursov-valyut/json/">CBU.uz website</a> to obtain an API key. Note down the API key that is provided.</li>
		<li>Rename the <code>example.env</code> file to <code>.env</code> and fill in the API token and API key that you obtained in the previous steps.</li>
		<li>Build the project using the following command:</li>
	</ol>
	<pre><code>mvn clean package</code></pre>
	<ol start="6">
		<li>Start the bot by running the following command:</li>
	</ol>
	<pre><code>java -jar target/Currency-Converter-Telegram-Bot-1.0-SNAPSHOT-jar-with-dependencies.jar</code></pre>
	<h2>Usage</h2>
	<p>Once the bot is running, users can interact with it via Telegram. To use the bot, follow these steps:</p>
	<ol>
		<li>Open a conversation with the bot by searching for the bot's name in the Telegram search bar.</li>
		<li>Type <code>/start</code> to initiate a conversation with the bot.</li>
		<li>Follow the instructions provided by the bot to perform currency conversions. To perform a currency conversion, type the following command:</li>
	</ol>
	<pre><code>/convert &lt;amount&gt; &lt;source_currency&gt; &lt;target_currency&gt;</code></pre>
	<p>Replace <code>&lt;amount&gt;</code> with the amount of currency you wish to convert, <code>&lt;source_currency&gt;</code> with the currency you wish to convert from, and <code>&lt;target_currency&gt;</code> with the currency you wish to convert to. You can use the three-letter currency code for each currency (e.g. USD for US Dollars, EUR for Euros).</p>
	<p>For example, to convert 10 USD to EUR, type the following command:</p>
	<pre><code>/convert 10 USD EUR</code></pre>
	<ol start="4">
