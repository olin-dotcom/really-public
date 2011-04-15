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

Heroku ENV
==========

Even better than putting these in files would be to:

  - in <code>app/models/comment.rb</code> specify credentials like <code>ENV['S3_ACCESS_KEY']</code>
  - then set [Heroku environment variables](http://devcenter.heroku.com/articles/config-vars#rack_env_rails_env_merb_env) with
	- <code>heroku config:add S3_ACCESS_KEY=YOUR_ACCESS_KEY</code>

Now, you don't need to stash your credentials in your git repo, ever.