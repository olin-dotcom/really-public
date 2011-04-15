Heroku compatible Paperclip + S3 app
====================================

This uses paperclip to handle files, S3 (optionally) to back the files, and can use Heroku for deployment as it has RMagick in the Gemfile.


To use S3
=========

Add a file in <code>/config/amazon_s3.yml</code> with contents that look like:

<pre>
development:
  access_key_id: YOUR_KEY_HERE
  secret_access_key: YOUR_SECRET_HERE

test:
  access_key_id: YOUR_KEY_HERE
  secret_access_key: YOUR_SECRET_HERE
  
production:
  access_key_id: YOUR_KEY_HERE
  secret_access_key: YOUR_SECRET_HERE

</pre>