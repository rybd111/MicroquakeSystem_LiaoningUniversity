/*
 * MATLAB Compiler: 6.6 (R2018a)
 * Date: Mon Jan 25 13:21:57 2021
 * Arguments: 
 * "-B""macro_default""-W""java:PSO,pso_locate""-T""link:lib""-d""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO\\for_testing""class{pso_locate:I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO.m}""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\bianyi.m""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\mubiao3.m""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\mysort3.m""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO.m"
 */

package PSO;

import com.mathworks.toolbox.javabuilder.pooling.Poolable;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The <code>pso_locateRemote</code> class provides a Java RMI-compliant interface to 
 * MATLAB functions. The interface is compiled from the following files:
 * <pre>
 *  I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>pso_locateRemote</code> 
 * instance when it is no longer needed to ensure that native resources allocated by this 
 * class are properly freed, and the server-side proxy is unexported.  (Failure to call 
 * dispose may result in server-side threads not being properly shut down, which often 
 * appears as a hang.)  
 *
 * This interface is designed to be used together with 
 * <code>com.mathworks.toolbox.javabuilder.remoting.RemoteProxy</code> to automatically 
 * generate RMI server proxy objects for instances of PSO.pso_locate.
 */
public interface pso_locateRemote extends Poolable
{
    /**
     * Provides the standard interface for calling the <code>PSO</code> MATLAB function 
     * with 2 input arguments.  
     *
     * Input arguments to standard interface methods may be passed as sub-classes of 
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of any 
     * supported Java type (i.e. scalars and multidimensional arrays of any numeric, 
     * boolean, or character type, or String). Arguments passed as Java types are 
     * converted to MATLAB arrays according to default conversion rules.
     *
     * All inputs to this method must implement either Serializable (pass-by-value) or 
     * Remote (pass-by-reference) as per the RMI specification.
     *
     * Documentation as provided by the author of the MATLAB function:
     * <pre>
     * % sample=[
     * % 4596627.472,41516836.655,21.545,2.13;%������S1
     * % 4595913.485,41519304.125,23.921,2.28;%��ҵ�㳡Z2
     * % 4595388.504,41518099.807,22.776,2.32;%�����T4
     * % 4597983.404,41520207.356,22.661,2.36;%�������W6
     * % 4594304.927,41518060.298,21.926,2.46;%������U3
     * % 4593163.619,41516707.440,22.564,2.61;%�Ϸ羮V7
     * %     ];
     * % sample=[
     * %      41517290.037,4599537.326,24.565,0; %����R2
     * %      41519304.125,4595913.485,23.921,0.08;%��ҵ�㳡Z1  7.11��8
     * %      41518060.298,4594304.927,21.926,0.23;%������U3
     * %      41520207.356,4597983.404,22.661,0.099999;%�������W4
     * % %      41520815.875,4597384.576,25.468,2.73;%���ϳ���X5
     * %      41519926.476,4597275.978,20.705,0.08999 %��ҩ��Y6
     * % %      41518099.807,4595388.504,22.776,0;%�����T4
     * % %      41516836.655,4596627.472,21.545,0;%������S??
     * % %      41516707.440,4593163.619,22.564,2.59;%�Ϸ羮V??
     * %       ];
     * % del=sample(1,:);
     * % for i=1:length(sample(1,:))
     * %     sample(:,i)=sample(:,i)-sample(1,i);
     * % end
     * % v=3850;%ϵͳ�ٶ�
     * </pre>
     *
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     *
     * @return Array of length nargout containing the function outputs. Outputs are 
     * returned as sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>. 
     * Each output array should be freed by calling its <code>dispose()</code> method.
     *
     * @throws java.rmi.RemoteException An error has occurred during the function call or 
     * in communication with the server.
     */
    public Object[] PSO(int nargout, Object... rhs) throws RemoteException;
  
    /** 
     * Frees native resources associated with the remote server object 
     * @throws java.rmi.RemoteException An error has occurred during the function call or in communication with the server.
     */
    void dispose() throws RemoteException;
}
