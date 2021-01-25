/*
 * MATLAB Compiler: 6.6 (R2018a)
 * Date: Mon Jan 25 13:21:57 2021
 * Arguments: 
 * "-B""macro_default""-W""java:PSO,pso_locate""-T""link:lib""-d""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO\\for_testing""class{pso_locate:I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO.m}""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\bianyi.m""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\mubiao3.m""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\mysort3.m""-a""I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO.m"
 */

package PSO;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.util.*;

/**
 * The <code>pso_locate</code> class provides a Java interface to MATLAB functions. 
 * The interface is compiled from the following files:
 * <pre>
 *  I:\\�о����׶�\\��ɽ\\���³���\\MicroquakeSystem_LiaoningUniversity\\�����㷨\\����Ⱥ�㷨\\��λ�����о�-8.4\\PSO.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>pso_locate</code> 
 * instance when it is no longer needed to ensure that native resources allocated by this 
 * class are properly freed.
 * @version 0.0
 */
public class pso_locate extends MWComponentInstance<pso_locate>
{
    /**
     * Tracks all instances of this class to ensure their dispose method is
     * called on shutdown.
     */
    private static final Set<Disposable> sInstances = new HashSet<Disposable>();

    /**
     * Maintains information used in calling the <code>PSO</code> MATLAB function.
     */
    private static final MWFunctionSignature sPSOSignature =
        new MWFunctionSignature(/* max outputs = */ 4,
                                /* has varargout = */ false,
                                /* function name = */ "PSO",
                                /* max inputs = */ 2,
                                /* has varargin = */ false);

    /**
     * Shared initialization implementation - private
     * @throws MWException An error has occurred during the function call.
     */
    private pso_locate (final MWMCR mcr) throws MWException
    {
        super(mcr);
        // add this to sInstances
        synchronized(pso_locate.class) {
            sInstances.add(this);
        }
    }

    /**
     * Constructs a new instance of the <code>pso_locate</code> class.
     * @throws MWException An error has occurred during the function call.
     */
    public pso_locate() throws MWException
    {
        this(PSOMCRFactory.newInstance());
    }
    
    private static MWComponentOptions getPathToComponentOptions(String path)
    {
        MWComponentOptions options = new MWComponentOptions(new MWCtfExtractLocation(path),
                                                            new MWCtfDirectorySource(path));
        return options;
    }
    
    /**
     * @deprecated Please use the constructor {@link #pso_locate(MWComponentOptions componentOptions)}.
     * The <code>com.mathworks.toolbox.javabuilder.MWComponentOptions</code> class provides an API to set the
     * path to the component.
     * @param pathToComponent Path to component directory.
     * @throws MWException An error has occurred during the function call.
     */
    public pso_locate(String pathToComponent) throws MWException
    {
        this(PSOMCRFactory.newInstance(getPathToComponentOptions(pathToComponent)));
    }
    
    /**
     * Constructs a new instance of the <code>pso_locate</code> class. Use this 
     * constructor to specify the options required to instantiate this component.  The 
     * options will be specific to the instance of this component being created.
     * @param componentOptions Options specific to the component.
     * @throws MWException An error has occurred during the function call.
     */
    public pso_locate(MWComponentOptions componentOptions) throws MWException
    {
        this(PSOMCRFactory.newInstance(componentOptions));
    }
    
    /** Frees native resources associated with this object */
    public void dispose()
    {
        try {
            super.dispose();
        } finally {
            synchronized(pso_locate.class) {
                sInstances.remove(this);
            }
        }
    }
  
    /**
     * Invokes the first MATLAB function specified to MCC, with any arguments given on
     * the command line, and prints the result.
     *
     * @param args arguments to the function
     */
    public static void main (String[] args)
    {
        try {
            MWMCR mcr = PSOMCRFactory.newInstance();
            mcr.runMain( sPSOSignature, args);
            mcr.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    /**
     * Calls dispose method for each outstanding instance of this class.
     */
    public static void disposeAllInstances()
    {
        synchronized(pso_locate.class) {
            for (Disposable i : sInstances) i.dispose();
            sInstances.clear();
        }
    }

    /**
     * Provides the interface for calling the <code>PSO</code> MATLAB function 
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
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
     * @param lhs List in which to return outputs. Number of outputs (nargout) is
     * determined by allocated size of this List. Outputs are returned as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code>
     * method.
     *
     * @param rhs List containing inputs. Number of inputs (nargin) is determined
     * by the allocated size of this List. Input arguments may be passed as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or
     * as arrays of any supported Java type. Arguments passed as Java types are
     * converted to MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void PSO(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sPSOSignature);
    }

    /**
     * Provides the interface for calling the <code>PSO</code> MATLAB function 
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
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
     * @param lhs array in which to return outputs. Number of outputs (nargout)
     * is determined by allocated size of this array. Outputs are returned as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code>
     * method.
     *
     * @param rhs array containing inputs. Number of inputs (nargin) is
     * determined by the allocated size of this array. Input arguments may be
     * passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void PSO(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sPSOSignature);
    }

    /**
     * Provides the standard interface for calling the <code>PSO</code> MATLAB function with 
     * 2 comma-separated input arguments.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
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
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] PSO(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sPSOSignature), 
                    sPSOSignature);
        return lhs;
    }
}
