Rails.application.config.middleware.use OmniAuth::Builder do  
  provider :twitter, 'yours', 'yours'  
  provider :facebook, 'yours', 'yours'  
end