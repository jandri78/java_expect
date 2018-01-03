package Test;

import static net.sf.expectit.matcher.Matchers.regexp;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import java.io.IOException;
import java.util.Properties;


public class SshLocalhostExample {
	public static void main(String[] args) throws JSchException, IOException {
		JSch jSch = new JSch();
		Session session = jSch.getSession("user", "127.0.0.1",2828);
		session.setPassword("1234");
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		Channel channel = session.openChannel("shell");
		channel.connect();
		Expect expect = new ExpectBuilder().withOutput(channel.getOutputStream())
				.withInputs(channel.getInputStream(), channel.getExtInputStream()).withEchoOutput(System.out)
				.withEchoInput(System.err).withExceptionOnFailure().build();

		try {
			
			expect.sendLine("Control start CLI");
			boolean managerR = expect.expect(regexp("ager")).isSuccessful();
			System.out.println(managerR);
			expect.sendLine("172.24.20.8:8080\n");
			
			boolean userR = expect.expect(regexp("User")).isSuccessful();
			System.out.println(userR);
			expect.sendLine("support");
			
			
			
			boolean passR = expect.expect(regexp("word")).isSuccessful();
			System.out.println(passR);
			expect.sendLine("support\r");
			/**
			boolean startConR = expect.expect(regexp("word")).isSuccessful();
			System.out.println(startConR);
			**/
			System.out.println(expect.expect(regexp("ok")).getBefore().trim());
			
			/** Testing OK
			expect.sendLine("ssh centos@localhost");
			boolean directory = expect.expect(regexp("assword")).isSuccessful();
			System.out.println(directory);

			expect.sendLine("centos\r");
			boolean Salida = expect.expect(regexp("gin")).isSuccessful();
			System.out.println(Salida);

			expect.sendLine("echo expect form java 2 >>text.txt\r");
			expect.sendLine("ls -ltrh");
			System.out.println(expect.expect(regexp("/root")).getBefore().trim());
			**/
			
			/**
			 * System.out.println(expect.expect(regexp("/root")).isSuccessful());
			 * System.out.println("------"); System.out.println("pwd1:" +
			 * expect.expect(times(2,
			 * contains("\n"))).getResults().get(1).getBefore());
			 * expect.sendLine("pwd"); // a regexp which captures the output of
			 * pwd System.out.println("pwd2:" +
			 * expect.expect(regexp("(?m)\\n([^\\n]*)\\n")).group(1));
			 * expect.expect(contains("$")); expect.sendLine("ls -l"); //
			 * skipping the echo command expect.expect(times(2,
			 * contains("\n"))); //getting the output of ls
			 **/

			expect.interact();
			expect.close();
		}

		catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception " + e.getMessage());

		}

		finally {
			expect.close();
			channel.disconnect();
			session.disconnect();
		}
	}
}