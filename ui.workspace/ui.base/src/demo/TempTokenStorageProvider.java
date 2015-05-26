package demo;

import java.util.ArrayList;
import java.util.List;

import org.amdatu.security.tokenprovider.Token;
import org.amdatu.security.tokenprovider.TokenStorageProvider;
import org.apache.felix.dm.annotation.api.Component;

// TODO: Need to create actual implementation that uses the database (PostgreSQL)
@Component
public class TempTokenStorageProvider implements TokenStorageProvider {
	private List<Token> tokens = new ArrayList<Token>();

	@Override
	public void addToken(Token token) {
		tokens.add(token);
	}

	@Override
	public void updateToken(Token token) {

	}

	@Override
	public Token getToken(String token) {
		Token result = null;
		for (Token stored : tokens) {
			if (stored.getToken().equals(token)) {
				result = stored;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean hasToken(String token) {
		return getToken(token) != null;
	}

	@Override
	public void removeToken(Token token) {
		if (tokens.contains(token)) {
			tokens.remove(token);
		}
	}

}
