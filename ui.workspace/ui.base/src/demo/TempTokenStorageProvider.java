package demo;

import org.amdatu.security.tokenprovider.Token;
import org.amdatu.security.tokenprovider.TokenStorageProvider;
import org.apache.felix.dm.annotation.api.Component;

@Component
public class TempTokenStorageProvider implements TokenStorageProvider {

	@Override
	public void addToken(Token token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateToken(Token token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Token getToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeToken(Token token) {
		// TODO Auto-generated method stub
		
	}

}
